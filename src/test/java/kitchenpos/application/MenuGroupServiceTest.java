package kitchenpos.application;

import kitchenpos.ApplicationMockTest;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static kitchenpos.fixture.MenuGroupFixture.NAME_추천메뉴;
import static kitchenpos.fixture.MenuGroupFixture.NAME_한마리메뉴;
import static kitchenpos.fixture.MenuGroupFixture.menuGroupCreateRequest;
import static kitchenpos.fixture.MenuGroupFixture.menuGroupResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("메뉴그룹 서비스 테스트")
@ApplicationMockTest
class MenuGroupServiceTest {
    @Mock
    private MenuGroupRepository menuGroupRepository;

    @InjectMocks
    private MenuGroupService menuGroupService;

    @DisplayName("메뉴그룹을 등록한다")
    @Test
    void creatMenuGroup() {
        // given
        MenuGroup request = menuGroupCreateRequest(NAME_추천메뉴);
        when(menuGroupRepository.save(any(MenuGroup.class))).thenReturn(menuGroupResponse(NAME_추천메뉴));

        // when
        MenuGroup result = menuGroupService.create(request);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(request.getName());
    }

    @DisplayName("메뉴그룹을 등록할 때, 이름이 공백이면 예외가 발생한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void creatMenuGroup_nullOrEmptyNameException(String name) {
        // given
        MenuGroup request = menuGroupCreateRequest(name);

        // when
        // then
        assertThatThrownBy(() -> menuGroupService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴그룹 목록을 볼 수 있다")
    @Test
    void getMenuGroups() {
        // given
        List<MenuGroup> menuGroups = List.of(menuGroupResponse(NAME_추천메뉴), menuGroupResponse(NAME_한마리메뉴));
        when(menuGroupRepository.findAll()).thenReturn(menuGroups);

        // when
        List<MenuGroup> result = menuGroupService.findAll();

        // then
        assertThat(result).hasSize(2);
    }
}
