package io.pivotal.microservices.windpark;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.microservices.exceptions.WindparkNotFoundException;

/**
 * A RESTFul controller for accessing windpark information.
 * 
 * @author Paul Chapman
 */
@RestController
public class WindparkController {

	protected Logger logger = Logger.getLogger(WindparkController.class
			.getName());

	@Autowired
	protected WindparkRepository windparkRepository;

	/**
	 * Create an instance plugging in the respository of Windpark.
	 * 
	 * @param windparkRepository
	 *            An windpark repository implementation.
	 */
	@Autowired
	public WindparkController(WindparkRepository windparkRepository) {
		this.windparkRepository = windparkRepository;
		logger.info("WindparkRepository says Hello.");

	//	logger.info("WindparkRepository says system has "
	//			+ windparkRepository.countWindpark() + " windpark");
	}

	/**
	 * Fetch an windpark with the specified windpark number.
	 * 
	 * @param windparkID
	 *            A numeric, 9 digit windpark number.
	 * @return The windpark if found.
	 * @throws WindparkNotFoundException
	 *             If the number is not recognised.
	 */
	@RequestMapping("/windpark/{windparkID}")
	public WindengineData byNumber(@PathVariable("windparkID") String windparkID) {

		logger.info("windpark-service byNumber() invoked: " + windparkID);
		List<WindengineData> listWindpark = windparkRepository.findByWindparkID(windparkID);
		logger.info("windpark-service findBzWindparkID() found: " + listWindpark.size() );

		if (listWindpark == null && listWindpark.size() > 0)
			throw new WindparkNotFoundException(windparkID);
		else {
			return listWindpark.get(0);
		}
	}

}
