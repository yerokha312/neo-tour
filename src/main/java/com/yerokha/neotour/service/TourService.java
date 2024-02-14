package com.yerokha.neotour.service;

import com.yerokha.neotour.dto.TourDto;
import com.yerokha.neotour.dto.TourDtoFromList;
import com.yerokha.neotour.entity.Month;
import com.yerokha.neotour.entity.Tour;
import com.yerokha.neotour.exception.NotFoundException;
import com.yerokha.neotour.repository.TourRepository;
import com.yerokha.neotour.util.TourMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    private static final int PAGE_SIZE = 12;
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public void addTour(Tour tour) {
        tourRepository.save(tour);
    }

    public TourDto getTourById(Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(NotFoundException::new);
        tour.setViewCount(tour.getViewCount() + 1);
        tourRepository.save(tour);
        return TourMapper.toDto(tour);
    }

    public List<TourDtoFromList> getPopularTours() {
        return tourRepository.findTop9ByOrderByViewCountDesc().stream()
                .map(TourMapper::tourDtoFromList).collect(Collectors.toList());
    }

    public List<TourDtoFromList> getMostVisitedTours() {
        return tourRepository.findTop9ByOrderByBookingCountDesc().stream()
                .map(TourMapper::tourDtoFromList).collect(Collectors.toList());
    }

    public Page<TourDtoFromList> getRecommendedTours(String month) {
        return tourRepository.findAllMonthOrderByBookingCountDesc(Month.valueOf(month), Pageable.ofSize(PAGE_SIZE))
                .map(TourMapper::tourDtoFromList);
    }

    public void updateTour(Tour tour) {
        tourRepository.save(tour);
    }

    public void deleteTourById(Long id) {
        tourRepository.deleteById(id);
    }

//
//    public Iterable<Tour> getToursByCountry(String country) {
//        return tourRepository.findAllByCountry(country);
//    }
}
