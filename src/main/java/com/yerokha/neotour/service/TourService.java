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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class TourService {

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
                months |= Months.ALL[month - 1];
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

    @Transactional
    protected void incrementBookingCount(Long tourId) {
        tourRepository.incrementBookingCount(tourId);
    }

    public Page<TourDtoFromList> getTours(String param, int page, int size) {
        if (page < 0 || size < 1) {
            throw new IllegalArgumentException("Page page or size");
        }
        Pageable pageable = PageRequest.of(page, size);
        return switch (param) {
            case "popular" -> getPopularTours(pageable);
            case "featured" -> getFeaturedTours(pageable);
            case "visited" -> getMostVisitedTours(pageable);
            default -> getToursByContinent(param, pageable);
        };
    }

    private Page<TourDtoFromList> getPopularTours(Pageable pageable) {
        return tourRepository.findAllByOrderByViewCountDesc(pageable)
                .map(TourMapper::toTourDtoFromList);
    }

    private Page<TourDtoFromList> getFeaturedTours(Pageable pageable) {
        return tourRepository.findAllByFeaturedTrue(Months.getCurrentMonthMask(), pageable)
                .map(TourMapper::toTourDtoFromList);
    }

    private Page<TourDtoFromList> getMostVisitedTours(Pageable pageable) {
        return tourRepository.findAllByOrderByBookingCountDesc(pageable)
                .map(TourMapper::toTourDtoFromList);
    }

    private Page<TourDtoFromList> getToursByContinent(String continent, Pageable pageable) {
        return tourRepository.findAllByLocation_Continent(continent, pageable)
                .map(TourMapper::toTourDtoFromList);
    }

    public Page<TourDtoFromList> getRecommendedTours(int month, int page, int size) {
        return tourRepository.findRecommendedTours(Months.getCurrentMonthMask(), PageRequest.of(page, size))
                .map(TourMapper::toTourDtoFromList);

    }

    public void updateTour(Tour tour) {
        tourRepository.save(tour);
    }

    public void deleteTourById(Long id) {
        tourRepository.deleteById(id);
    }
}
