package loyality.loyalityservice.service.impl;

import lombok.AllArgsConstructor;
import loyality.loyalityservice.dto.GroupDto;
import loyality.loyalityservice.entity.Group;
import loyality.loyalityservice.exception.ResourceNotFoundException;
import loyality.loyalityservice.mapper.GroupMapper;
import loyality.loyalityservice.repository.ClientAccountRepository;
import loyality.loyalityservice.repository.GroupRepository;
import loyality.loyalityservice.service.GroupService;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private ClientAccountRepository clientRepository;

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
            //setCondition
            if(groupDto.getCondition() !=null && groupDto.getCondition() >= 1){
                group.setCondition(groupDto.getCondition());
            }else {group.setCondition(100);}
            //setDiscount
            if(groupDto.getDiscount() !=null && groupDto.getDiscount() >= 1 && groupDto.getDiscount() <= 100){
                group.setDiscount(groupDto.getDiscount());
            }else {group.setDiscount(10);}
            //setAvailablePart
            if(groupDto.getAvailablePart() !=null && groupDto.getAvailablePart() >= 1 && groupDto.getAvailablePart() <= 100){
                group.setAvailablePart(groupDto.getAvailablePart());
            }else {group.setAvailablePart(50);}
            //group.setCondition(groupDto.getCondition() !=null ? groupDto.getCondition() : 100);
            //group.setDiscount(groupDto.getDiscount() !=null ? groupDto.getDiscount() : 10);
            //group.setAvailablePart(groupDto.getAvailablePart() !=null ? groupDto.getAvailablePart() : 50);

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

        //валидация параметров
        if(updatedGroup.getDiscount() !=null && updatedGroup.getDiscount() >= 1 && updatedGroup.getDiscount() <= 100){
            updatedGroup.setDiscount(updatedGroup.getDiscount());
        }else {
            throw new ResourceNotFoundException("Значение должно быть от 1 до 100!");
        }
        //setAvailablePart
        if(updatedGroup.getAvailablePart() !=null && updatedGroup.getAvailablePart() >= 1 && updatedGroup.getAvailablePart() <= 100){
            updatedGroup.setAvailablePart(updatedGroup.getAvailablePart());
        }else {
            throw new ResourceNotFoundException("Значение должно быть от 1 до 100!");
        }

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

    @Override
    public void deleteGroup(Long companyId, Long groupId) {
        //крайне не рекомендуется удалять группу после ее создания.
        //так как у клиентов сбросится группа и станет группа по умолчанию.

        //проверка, что группа существует вообще, иначе прекратить обработку.
        if (groupRepository.findById(groupId).isPresent()){
            //получаем группу по умолчанию в компании
            Long defGroupId = groupRepository.getDefGroup(companyId);
            //если запрос удалить группу по умолчанию, кидаем ошибку
            if (Objects.equals(groupId, defGroupId)){
                throw new ResourceNotFoundException("Нельзя удалить группу, которая является группой по умолчанию!");
            }else {
                //если запрос удалить группу не по умолчанию, присваиваем клиентам с той группы группу по умолчанию
                clientRepository.setGroup(companyId, groupId, defGroupId);
            }
            //удаляем группу
            groupRepository.deleteById(groupId);
        }else{
            throw new ResourceNotFoundException("Указанной в запросе группы не существует!");
        }
    }



    //   @Override
//    public GroupDto getGroupByClient(Long clientId) {
//        Group g =  groupRepository.findByClientId(clientId);
//        return GroupMapper.mapToGroupDto(g);
//    }

}
