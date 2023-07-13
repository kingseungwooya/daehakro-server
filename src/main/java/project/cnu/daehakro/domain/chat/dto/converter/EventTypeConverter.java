package project.cnu.daehakro.domain.chat.dto.converter;


import org.springframework.core.convert.converter.Converter;
import project.cnu.daehakro.domain.enums.EventType;

public class EventTypeConverter implements Converter<String, EventType> {

    @Override
    public EventType convert(String source) {
        return EventType.of(source);
    }

}
