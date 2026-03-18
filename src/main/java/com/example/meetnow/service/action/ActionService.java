package com.example.meetnow.service.action;

import com.example.meetnow.service.model.ActionType;
import com.example.meetnow.service.model.UserAction;
import com.example.meetnow.service.model.UserActionLog;
import com.example.meetnow.service.repository.UserActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActionService {

    private final UserActionRepository actionRepository;

    private final ActionTypeRepository actionTypeRepository;

    public void save(UserActionLog action) {
        Optional<ActionType> actionType = actionTypeRepository.findById(action.getActionType().getId());
        if (actionType.isEmpty()) {
            log.error("Action type with id: {}, dont found", action.getActionType().getId());
            return;
        }

        if (isAlreadyLogged(action)) {
            log.error("This action already logged. Action = {}", action);
            return;
        }

        UserAction userAction = UserAction.builder()
                .userId(action.getUserId())
                .eventId(action.getEventId())
                .actionType(actionType.get())
                .actionTime(action.getActionTime())
                .build();

        actionRepository.save(userAction);
    }

    private boolean isAlreadyLogged(UserActionLog action) {
        int count = actionRepository.countByUserIdAndEventIdAndActionTypeId(action.getUserId(),
                action.getEventId(),
                action.getActionType().getId()
        );

        return count != 0;
    }
}
