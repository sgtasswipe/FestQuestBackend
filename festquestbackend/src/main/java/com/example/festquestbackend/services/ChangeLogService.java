package com.example.festquestbackend.services;

import com.example.festquestbackend.models.misc.ChangeLog;
import com.example.festquestbackend.models.quests.Quest;
import com.example.festquestbackend.repositories.misc.ChangeLogRepository;
import com.example.festquestbackend.repositories.quests.QuestRepository;
import com.example.festquestbackend.repositories.users.FestUserRepository;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class ChangeLogService {

    private final QuestRepository questRepository;
    private final FestUserRepository userRepository;
    private final ChangeLogRepository changeLogRepository;

    public ChangeLogService(QuestRepository questRepository, FestUserRepository userRepository, ChangeLogRepository changeLogRepository) {
        this.questRepository = questRepository;
        this.userRepository = userRepository;
        this.changeLogRepository = changeLogRepository;
    }
//TODO
//  Generic method. entity = '"Quest", "FestUser"...' action = '"created", "updated"...'
    public <T> void insertChangeToLogs(T entity,
//                                       FestUser user,
                                       String action) {
        ChangeLog newChangeLog = new ChangeLog();
//        newChangeLog.setUser(user);
        newChangeLog.setTimeStamp(LocalDateTime.now());

        // Build a generic change description
        String entityName = entity.getClass().getSimpleName();
        String changeDescription = buildChangeDescription(entity, action);

//        newChangeLog.setChange(user.getFirstName() + " " + user.getLastName() + " " + action + " a " + entityName + ": " + changeDescription);

        changeLogRepository.save(newChangeLog);
    }

//  Formattes the change description in a generic way. Up for improvement
    private <T> String buildChangeDescription(T entity, String action) {
        StringBuilder description = new StringBuilder();

        // Use reflection(Field) to get all fields and values of the entity
        try {
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value != null) {
                    description.append(field.getName())
                            .append(": ")
                            .append(value.toString())
                            .append(", ");
                }
            }
            // Remove trailing comma
            if (description.length() > 0) {
                description.setLength(description.length() - 2);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return description.toString();
    }

}
