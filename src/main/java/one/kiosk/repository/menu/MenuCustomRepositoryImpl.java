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
import one.kiosk.vo.menu.MenuVo;
import one.kiosk.vo.menu.OptionVo;

import java.util.List;

import static com.querydsl.core.types.Projections.list;

@RequiredArgsConstructor
public class MenuCustomRepositoryImpl implements MenuCustomRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public List<MenuVo> findMenuList(Member member) {
        QMenu menu = QMenu.menu;
        QOption option = QOption.option1;
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
}
