package com.yerokha.neotour.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yerokha.neotour.dto.CreateTourDto;
import com.yerokha.neotour.dto.TourDto;
import com.yerokha.neotour.dto.TourDtoFromList;
import com.yerokha.neotour.entity.Tour;
import com.yerokha.neotour.exception.NotFoundException;
import com.yerokha.neotour.repository.TourRepository;
import com.yerokha.neotour.util.Months;
import com.yerokha.neotour.util.TourMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class TourService {

    private static final int PAGE_SIZE = 12;
    private final TourRepository tourRepository;
    private final ImageService imageService;

    private final ObjectMapper mapper = new ObjectMapper();

    public TourService(TourRepository tourRepository, ImageService imageService) {
        this.tourRepository = tourRepository;
        this.imageService = imageService;
    }

    public void addTour(String json, List<MultipartFile> images) {
        CreateTourDto dto;
        try {
            dto = mapper.readValue(json, CreateTourDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Tour tour = TourMapper.fromDto(dto);
        int months = 0;
        if (dto.months() != null) {
            for (int month : dto.months()) {
                months |= Months.ALL_ARR[month - 1];
            }
        }

        tour.setRecommendedMonths(months);
        for (MultipartFile image : images) {
            tour.addImage(imageService.processImage(image));
        }
        tourRepository.save(tour);
    }

    @Transactional
    public TourDto getTourById(Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(NotFoundException::new);
        incrementViewCount(id);
        return TourMapper.toDto(tour);
    }

    @Transactional
    protected void incrementViewCount(Long id) {
        tourRepository.incrementViewCount(id);
    }

    public List<TourDtoFromList> getTours(String param) {
        return switch (param) {
            case "popular" -> getPopularTours();
            case "featured" -> getFeaturedTours();
            case "visited" -> getMostVisitedTours();
            default -> getToursByContinent(param);
        };
    }

    private List<TourDtoFromList> getPopularTours() {
        return tourRepository.findTop9ByOrderByViewCountDesc().stream()
                .map(TourMapper::toTourDtoFromList).toList();
    }

    private List<TourDtoFromList> getFeaturedTours() {
        return tourRepository.findAllByFeaturedTrue(Months.ALL_ARR[LocalDate.now().getMonthValue() - 1]).stream().map(TourMapper::toTourDtoFromList).toList();
    }

    private List<TourDtoFromList> getMostVisitedTours() {
        return tourRepository.findTop9ByOrderByBookingCountDesc().stream()
                .map(TourMapper::toTourDtoFromList).toList();
    }

    private List<TourDtoFromList> getToursByContinent(String continent) {
        return tourRepository.findAllByLocation_Continent(continent).stream()
                .map(TourMapper::toTourDtoFromList).toList();
    }

    public Page<TourDtoFromList> getRecommendedTours(int month) {
        int monthMask = Months.ALL_ARR[month - 1];

        return tourRepository.findRecommendedTours(monthMask, Pageable.ofSize(PAGE_SIZE))
                .map(TourMapper::toTourDtoFromList);

    }

    public void updateTour(Tour tour) {
        tourRepository.save(tour);
    }

    public void deleteTourById(Long id) {
        tourRepository.deleteById(id);
    }
}
