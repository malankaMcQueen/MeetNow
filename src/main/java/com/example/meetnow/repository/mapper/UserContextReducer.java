package com.example.meetnow.repository.mapper;

import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.UserContext;
import org.jdbi.v3.core.result.RowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.meetnow.repository.mapper.ColumnNames.*;
import static com.example.meetnow.repository.mapper.MapperUtil.extractInterest;

public class UserContextReducer implements RowReducer<Map<Long, UserContext>, UserContext> {

    @Override
    public Map<Long, UserContext> container() {
        return new HashMap<>();
    }

    @Override
    public void accumulate(Map<Long, UserContext> container, RowView rowView) {
        Long userId = rowView.getColumn(USER_ID, Long.class);
        UserContext userContext = container.get(userId);

        if (userContext == null) {
            userContext = UserContext.builder().id(userId).interests(new HashSet<>()).build();
        }
        Optional<Interest> interest = extractInterest(rowView);

        if (interest.isPresent()) {
            userContext.getInterests().add(interest.get());
        }

        container.put(userId, userContext);
    }

    @Override
    public Stream<UserContext> stream(Map<Long, UserContext> container) {
        return container.values().stream();
    }

}
