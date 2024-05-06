package io.namoosori.travelclub.web.store.jpastore;

import io.namoosori.travelclub.web.aggregate.club.TravelClub;
import io.namoosori.travelclub.web.store.ClubStore;
import io.namoosori.travelclub.web.store.jpastore.jpo.TravelClubJpo;
import io.namoosori.travelclub.web.store.jpastore.repository.ClubRepository;
import io.namoosori.travelclub.web.util.exception.NoSuchClubException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ClubJpaStore implements ClubStore {

    private ClubRepository clubRepository;

    public ClubJpaStore(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public String create(TravelClub club) {
        clubRepository.save(new TravelClubJpo(club));
        return club.getId();
    }

    @Override
    public TravelClub retrieve(String clubId) {
        // Id로 클럽을 찾는 메소드
        Optional<TravelClubJpo> clubJpo = clubRepository.findById(clubId);
        if(!clubJpo.isPresent()) {
            throw new NoSuchClubException(String.format("TravelClub(No) is not found.", clubId));
        }
        return clubJpo.get().toDomain();
    }

    @Override
    public List<TravelClub> retrieveAll() {
        // 전체 club을 찾는 메소드
        List<TravelClubJpo> clubJpos = clubRepository.findAll();
//        return clubJpos.stream().map(clubJpo -> clubJpo.toDomain()).collect(Collectors.toList());
          return clubJpos.stream().map(TravelClubJpo::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<TravelClub> retrieveByName(String name) {
        // 검색은 아주 다양한 옵션으로 할 수 있다.
        List<TravelClubJpo> clubJpos = clubRepository.findAllByName(name);
        return clubJpos.stream().map(TravelClubJpo::toDomain).collect(Collectors.toList());

    }



    @Override
    public void update(TravelClub club) {
        // save() 메소드는 insert 뿐만 아니라 update도 같이함.
        // 즉, 해당 데이터로 select를 먼저 한 다음
        // 해당 데이터가 없다면 새로 create하고, 데이터가 있다면 기존의 것을 새로운 것으로 update해줌.
        clubRepository.save(new TravelClubJpo(club));
    }

    @Override
    public void delete(String clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public boolean exists(String clubId) {
        return clubRepository.existsById(clubId);
    }
}
