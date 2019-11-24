package pl.com.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.app.dto.DietRateParameterDTO;
import pl.com.app.exception.ExceptionCode;
import pl.com.app.exception.MyException;
import pl.com.app.mappers.DietRateParameterMapper;
import pl.com.app.model.DietRateParameter;
import pl.com.app.repository.DietRateParameterRepository;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DietRateParameterService {
    private final DietRateParameterMapper dietRateParameterMapper;
    private final DietRateParameterRepository dietRateParameterRepository;


    public DietRateParameterDTO findOneDietRateParameter(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "DIET RATE PARAMETER ID IS NULL");
        }
        DietRateParameter dietRateParameter = dietRateParameterRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "diet rate parameter with id " + id + " doesn't exist"));
        return dietRateParameterMapper.dietRateParameterToDietRateParameterDTO(dietRateParameter);
    }

    @Transactional
    public DietRateParameterDTO addDietRateParameter(DietRateParameterDTO dietRateParameterDTO) {
        if (dietRateParameterDTO == null) {
            throw new MyException(ExceptionCode.SERVICE, "DIET RATE PARAMETER IS NULL");
        }
        DietRateParameter dietRateParameterSaved = dietRateParameterRepository
                .save(dietRateParameterMapper.dietRateParameterDTOToDietRateParameter(dietRateParameterDTO));
        return dietRateParameterMapper.dietRateParameterToDietRateParameterDTO(dietRateParameterSaved);
    }

    @Transactional
    public DietRateParameterDTO updateDietRateParameter(Long id, DietRateParameterDTO dietRateParameterDTO) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "DIET RATE PARAMETER ID IS NULL");
        }
        if (dietRateParameterDTO == null) {
            throw new MyException(ExceptionCode.SERVICE, "DIET RATE PARAMETER IS NULL");
        }
        DietRateParameter dietRateParameter = dietRateParameterRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "diet rate parameter with id " + id + " doesn't exist"));

        dietRateParameter.setAveragePutOnValue(dietRateParameterDTO.getAveragePutOnValue() == null ? dietRateParameter.getAveragePutOnValue() : dietRateParameterDTO.getAveragePutOnValue());
        dietRateParameter.setAverageSlimValue(dietRateParameterDTO.getAverageSlimValue() == null ? dietRateParameter.getAverageSlimValue() : dietRateParameterDTO.getAverageSlimValue());
        dietRateParameter.setFastPutOnValue(dietRateParameterDTO.getFastPutOnValue() == null ? dietRateParameter.getFastPutOnValue() : dietRateParameterDTO.getFastPutOnValue());
        dietRateParameter.setFastSlimValue(dietRateParameterDTO.getFastSlimValue() == null ? dietRateParameter.getFastSlimValue() : dietRateParameterDTO.getFastSlimValue());

        DietRateParameter dietRateParameterFromDb = dietRateParameterRepository.save(dietRateParameter);
        return dietRateParameterMapper.dietRateParameterToDietRateParameterDTO(dietRateParameterFromDb);
    }

    @Transactional
    public DietRateParameterDTO deleteDietRateParameter(Long id) {
        if (id == null) {
            throw new MyException(ExceptionCode.SERVICE, "DIET RATE PARAMETER ID IS NULL");
        }
        DietRateParameter dietRateParameter = dietRateParameterRepository
                .findById(id)
                .orElseThrow(() -> new MyException(ExceptionCode.SECURITY, "diet rate parameter with id " + id + " doesn't exist"));
        dietRateParameterRepository.delete(dietRateParameter);
        return dietRateParameterMapper.dietRateParameterToDietRateParameterDTO(dietRateParameter);
    }
}
