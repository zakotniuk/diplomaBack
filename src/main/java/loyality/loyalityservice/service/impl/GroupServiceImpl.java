package loyality.loyalityservice.service.impl;

import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.GroupDto;
import loyality.loyalityservice.entity.Group;
import loyality.loyalityservice.exception.ResourceNotFoundException;
import loyality.loyalityservice.mapper.GroupMapper;
import loyality.loyalityservice.repository.GroupRepository;
import loyality.loyalityservice.service.GroupService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    @Override
    //клиенты ищутся по определенной компании!
    public List<GroupDto> getAllGroups(Long companyId) {
        List<Group> groups = groupRepository.findByCompanyId(companyId);
        return groups.stream().map((group) -> GroupMapper.mapToGroupDto(group))
                .collect(Collectors.toList());
    }

    @Override
    //группа создается в определенной компании!
    public GroupDto createGroup(Long companyId, GroupDto groupDto) {

        //Лимит на кол-во групп в компании
        Long col = groupRepository.findByCompanyId(companyId).stream().count();
        System.out.println("col:"+ col);

        Group group = null, savedGroup = null;

        if (col < 5 || col == null){
            group = GroupMapper.mapToGroup(groupDto);
            group.setCompanyId(companyId);
            group.setGroupName(groupDto.getGroupName() != null ? groupDto.getGroupName() : "Новая группа");
            group.setCondition(100);
            group.setDiscount(10);
            //если у компании еще нет групп, то ставим новую группу группой по умолчанию
            if(groupRepository.findByCompanyId(companyId) == null || groupRepository.findByCompanyId(companyId).isEmpty()){
                group.setIsDefault(true);
            }else{
                //иначе оставляем не дефолтной
                group.setIsDefault(false);
            }
            savedGroup = groupRepository.save(group);
        }else{
            throw new ResourceNotFoundException("Лимит по количеству групп - 5 групп!");
        }

        return GroupMapper.mapToGroupDto(savedGroup);
    }

    @Override
    public void resetDefaultGroup(Long companyId){
        //Функция для сброса группы по умолчанию в компании
        Long defGroupId = groupRepository.getDefGroup(companyId);
        System.out.println("defGroupId:"+ defGroupId);

        Group group = groupRepository.findById(defGroupId).orElseThrow(() ->
                new ResourceNotFoundException("not exist def group"));
        group.setIsDefault(false);
        groupRepository.save(group);

    }

    @Override
    public GroupDto updateGroup(Long companyId, Long groupId, GroupDto updatedGroup) {

        //проверка, существует ли group
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Group is not exist with id: "+ groupId));

        //класс для обновления полей в объекте
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        ////Проверка изменения группы по умолчанию////
        //Если в запросе не указали изменение дефолтности групппы
        if (updatedGroup.getIsDefault()==null) {
            //то так и оставляем его null
            updatedGroup.setIsDefault(null);
            //Иначе если в запросе указали убрать дефолтность, а группа дефолтная
        } else if (updatedGroup.getIsDefault() == false && group.getIsDefault() == true) {
            //кидаем ошибку, тк нельзя убрать флаг умолчания у группы, можно только указать его в другой группе
            throw new ResourceNotFoundException("Нельзя убрать флаг умолчания у группы, можно только указать его у нужной!");
            //иначе если в запросе указали не дефолтную группу сделать дефолтной
        }else if (updatedGroup.getIsDefault() == true && group.getIsDefault() == false){
            // сбросываем текущую группу по умолчанию
            resetDefaultGroup(companyId);
            //и сделать выбранную группу группой по умолчанию
            updatedGroup.setIsDefault(true);
        }

        //Проверка, что группу не присвоят другой компании//
        if(updatedGroup.getCompanyId() != null && updatedGroup.getCompanyId() != group.getCompanyId() ){
            throw new ResourceNotFoundException("Нельзя присвоить группу чужой компании!");
        }

        //записываем в объект только измененные поля
        modelMapper.map(updatedGroup, group);
        //сохраняем объект
        Group updatedGroupObj = groupRepository.save(group);
        return GroupMapper.mapToGroupDto(updatedGroupObj);
    }
}
