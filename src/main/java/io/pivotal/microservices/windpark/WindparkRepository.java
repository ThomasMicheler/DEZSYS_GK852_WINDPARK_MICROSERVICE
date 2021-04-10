package io.pivotal.microservices.windpark;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 * Repository for Windpark data implemented using Spring Data JPA.
 * 
 * @author Paul Chapman
 */
public interface WindparkRepository extends Repository<Windpark, Long> {
	/**
	 * Find an windpark with the specified windpark number.
	 *
	 * @param windparkNumber
	 * @return The windpark if found, null otherwise.
	 */
	public Windpark findByNumber(String windparkNumber);

	/**
	 * Find windpark whose owner name contains the specified string
	 * 
	 * @param partialName
	 *            Any alphabetic string.
	 * @return The list of matching windpark - always non-null, but may be
	 *         empty.
	 */
	public List<Windpark> findByOwnerContainingIgnoreCase(String partialName);

	/**
	 * Fetch the number of windpark known to the system.
	 * 
	 * @return The number of windpark.
	 */
	@Query("SELECT count(*) from Windpark")
	public int countWindpark();
}
