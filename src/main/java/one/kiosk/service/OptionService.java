package one.kiosk.service;

import lombok.RequiredArgsConstructor;
import one.kiosk.entity.Menu;
import one.kiosk.entity.Option;
import one.kiosk.enums.Options;
import one.kiosk.repository.menu.OptionJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionJpaRepository optionJpaRepository;

    public void saveMenuOptions(Menu menu, List<Options> options) {
        for(Options option: options) {
            optionJpaRepository.save(Option.builder().option(option).menu(menu).build());
        }
    }

}
