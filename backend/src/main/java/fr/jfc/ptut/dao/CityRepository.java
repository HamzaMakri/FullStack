package fr.jfc.ptut.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.jfc.ptut.entity.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {

    /**
     * Trouve une ville par son nom.
     * Cette méthode sera automatiquement implémentée par Spring Data JPA.
     * @param cityName
     * @return La ville correspondant au nom fourni, ou null si pas trouvé.
     */
    public City findByName(String cityName);

    @Query(nativeQuery = true, value = "SELECT * FROM City Where country_id = :id")
    public List<City> findByCountryId(@Param("id") String id);

}
