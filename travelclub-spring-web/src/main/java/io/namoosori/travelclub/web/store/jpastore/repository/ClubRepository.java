package io.namoosori.travelclub.web.store.jpastore.repository;

import io.namoosori.travelclub.web.store.jpastore.jpo.TravelClubJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<TravelClubJpo, String> {
// 첫번째 제네릭은 실제로 entity로 정의된 객체가 들어가고, 두번째로 들어가는 제네릭은 식별자로 들어가는 타입이다.
    // 우리는 id를 식별자로 했으므로, id의 타입인 String이 들어감.
    List<TravelClubJpo> findAllByName(String name);
}
