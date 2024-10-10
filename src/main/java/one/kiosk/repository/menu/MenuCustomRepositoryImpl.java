package one.kiosk.repository.menu;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import one.kiosk.entity.Member;
import one.kiosk.entity.Menu;
import one.kiosk.entity.QMenu;
import one.kiosk.entity.QOption;
import one.kiosk.enums.Status;
import one.kiosk.exception.GlobalExceptionHandler;
import one.kiosk.vo.menu.MenuVo;
import one.kiosk.vo.menu.OptionVo;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.querydsl.core.types.Projections.list;

@RequiredArgsConstructor
public class MenuCustomRepositoryImpl implements MenuCustomRepository{

    private final JPAQueryFactory queryFactory;


    QMenu menu = QMenu.menu;
    QOption option = QOption.option1;

    @Override
    public List<MenuVo> findMenuList(Member member) {
        return queryFactory.selectFrom(menu)
                .where(menu.member.id.eq(member.getId()))
                .leftJoin(menu.options, option)
                .transform(GroupBy.groupBy(menu.id)
                        .list(
                                Projections.constructor(
                                        MenuVo.class,
                                        menu.id,
                                        menu.name,
                                        menu.price,
                                        menu.category,
                                        menu.status,
                                        menu.img,
                                        GroupBy.list(
                                                option.option
                                        )
                                )
                        )
                );

    }

    @Override
    public MenuVo findMenu(Long id, Member member) {
        List<MenuVo> findMenu = queryFactory.selectFrom(menu)
                .where(menu.id.eq(id), menu.member.id.eq(member.getId()))
                .leftJoin(menu.options, option)
                .transform(GroupBy.groupBy(menu.id)
                        .list(
                                Projections.constructor(
                                        MenuVo.class,
                                        menu.id,
                                        menu.name,
                                        menu.price,
                                        menu.category,
                                        menu.status,
                                        menu.img,
                                        GroupBy.list(
                                                option.option
                                        )
                                )
                        )
                );
        if(findMenu.size() != 1){
            throw new GlobalExceptionHandler.CustomException("등록된 메뉴가 아닙니다.", HttpStatus.BAD_REQUEST);
        }
        return findMenu.get(0);
    }
}
