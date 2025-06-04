package edu.uoc.epcsd.user.infrastructure.repository.jpa;

import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.repository.DigitalItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DigitalItemRepositoryImpl implements DigitalItemRepository {

    private final SpringDataDigitalItemRepository jpaDigitalItemRepository;

    private final SpringDataDigitalSessionRepository jpaDigitalSessionRepository;

    @Override
    public List<DigitalItem> findAllDigitalItem() {
        return jpaDigitalItemRepository.findAll().stream().map(DigitalItemEntity::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<DigitalItem> getDigitalItemById(Long id) {
        return jpaDigitalItemRepository.getDigitalItemById(id).map(DigitalItemEntity::toDomain);
    }
    
    @Override
    public List<DigitalItem> findDigitalItemBySession(Long digitalSessionId) {
        return jpaDigitalItemRepository.findDigitalItemByDigitalSession(digitalSessionId).stream().map(DigitalItemEntity::toDomain).collect(Collectors.toList());
    }   

    @Override
    public Long createDigitalItem(DigitalItem digitalItem) {
        DigitalSessionEntity digitalSessionEntity = jpaDigitalSessionRepository.findById(digitalItem.getDigitalSessionId()).orElseThrow(IllegalArgumentException::new);
        DigitalItemEntity digitalItemEntity = DigitalItemEntity.fromDomain(digitalItem);
        digitalItemEntity.setDigitalSession(digitalSessionEntity);
        
        return jpaDigitalItemRepository.save(digitalItemEntity).getId(); 
    }

	@Override
	public Long updateDigitalItem(DigitalItem digitalItem) {
        DigitalItemEntity digitalItemEntity = jpaDigitalItemRepository.findById(digitalItem.getId()).orElseThrow(IllegalArgumentException::new);
        
        DigitalSessionEntity digitalSessionEntity = jpaDigitalSessionRepository.findById(digitalItem.getDigitalSessionId()).orElseThrow(IllegalArgumentException::new);
        digitalItemEntity.setDigitalSession(digitalSessionEntity);
        digitalItemEntity.setDescription(digitalItem.getDescription());
        digitalItemEntity.setLat(digitalItem.getLat());
        digitalItemEntity.setLon(digitalItem.getLon());
        digitalItemEntity.setLink(digitalItem.getLink());
        digitalItemEntity.setStatus(digitalItem.getStatus());
        
        return jpaDigitalItemRepository.save(digitalItemEntity).getId(); 
	}

	@Override
	public void removeDigitalItem(DigitalItem digitalItem) {   
		jpaDigitalItemRepository.delete(DigitalItemEntity.fromDomain(digitalItem));		
	}
	
    
}
