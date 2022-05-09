package pl.wsb.java.flightapp.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import pl.wsb.java.flightapp.model.FlightGroup;
import pl.wsb.java.flightapp.model.FlightGroupRepository;
import pl.wsb.java.flightapp.model.FlightRepository;
import pl.wsb.java.flightapp.model.projection.GroupReadModel;
import pl.wsb.java.flightapp.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;



public class FlightGroupService {
    private FlightGroupRepository repository;
    private FlightRepository flightRepository;


    public FlightGroupService(final FlightGroupRepository repository, final FlightRepository flightRepository) {
        this.repository = repository;
        this.flightRepository=flightRepository;



    }

    public GroupReadModel createGroup(GroupWriteModel source){
        FlightGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
        if(flightRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone flights");
        }
        FlightGroup result = repository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("Flight group not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }
}
