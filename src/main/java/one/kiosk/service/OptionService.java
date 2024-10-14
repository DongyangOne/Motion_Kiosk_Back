package one.kiosk.service;

import lombok.RequiredArgsConstructor;
import one.kiosk.entity.Menu;
import one.kiosk.entity.Option;
import one.kiosk.enums.Options;
import one.kiosk.repository.menu.OptionJpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionJpaRepository optionJpaRepository;

    public List<Option> saveMenuOptions(Menu menu, List<Options> options) {
        List<Option> roptions = new ArrayList<>(options.size());
        for(Options option: options) {
            roptions.add(Option.builder().option(option).menu(menu).build());
        }
        optionJpaRepository.saveAll(roptions);
        return roptions;
    }

    public void deleteByMenuId(Long menuId) {
        optionJpaRepository.deleteByMenuId(menuId);
    }

}
