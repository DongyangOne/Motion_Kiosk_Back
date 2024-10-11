package one.kiosk.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import one.kiosk.dto.MenuOptionReturnDto;
import one.kiosk.dto.MenuReturnDto;
import one.kiosk.entity.MenuEntity;
import one.kiosk.entity.QImage;
import one.kiosk.entity.QMenuEntity;
import one.kiosk.entity.QMenuOptionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MenuJpaRepositoryImpl implements  MenuJpaRpositoryCustom{
    private final JPAQueryFactory queryFactory;

    //메뉴 전체조회 리스트 생성, 옵션dto 리스트 따로 생성 후 메뉴 전체조회 리스트 항목 한개당 옵션dto 넣기
    @Override
    public List<MenuReturnDto> findAllMenuWithOptionsByAdminId(Long adminId) {
        QMenuEntity menu = QMenuEntity.menuEntity;
        QMenuOptionEntity option = QMenuOptionEntity.menuOptionEntity;
        QImage image = QImage.image;

        // 모든 메뉴 조회
        List<MenuEntity> menus = queryFactory
                .selectFrom(menu)
                .leftJoin(menu.imageId, image).fetchJoin()  // 메뉴와 이미지를 조인
                .where(menu.adminId.eq(adminId))
                .fetch();

        //모든 옵션 조회후 옵션 dto리스트 생성
        List<MenuOptionReturnDto> options = queryFactory
                .selectFrom(option)
                .fetch()
                .stream()
                .map(o -> new MenuOptionReturnDto(o.getOptionname(), o.getPrice()))
                .collect(Collectors.toList());

        // 각 메뉴 항목에 옵션 리스트를 넣어 DTO로 변환
        return menus.stream()
                .map(m -> new MenuReturnDto(
                        m.getMenuname(),
                        m.getPrice(),
                        m.getCategory(),
                        m.getImageId() != null ? m.getImageId().getImageUrl() : null,
                        options  // 메뉴 항목 하나당 모든 옵션들 포함
                ))
                .collect(Collectors.toList());

    }
}
