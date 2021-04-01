package com.mt.bulkregistration;

import com.mt.bulkregistration.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("Test")
class BulkregistrationApplicationTests {
	@Autowired
	UserItemProcessor processor;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldProcessSuccessfully() throws Exception {
		User user = new User("+9999",
				User.SIM_TYPE.POSTPAID,
				"name",
				getDate("2018-05-05"),
				User.GENDER.F,
				"Address dlgj sdfs sdf kj",
				"IDNUMBER7");
		User processedUser = processor.process(user);
		Assert.assertNotNull(processedUser);
	}

	@Test
	void shouldFailOnEmptyMsisdn() throws Exception {
		User user = new User(null,
				User.SIM_TYPE.POSTPAID,
				"name",
				getDate("2018-05-05"),
				User.GENDER.F,
				"Address dlgj sdfs sdf kj",
				"IDNUMBER7");
		User processedUser = processor.process(user);
		Assert.assertNull(processedUser);
	}

	@Test
	void shouldFailOnSpecialCharOnName() throws Exception {
		User user = new User("+9999",
				User.SIM_TYPE.POSTPAID,
				"name!",
				getDate("2018-05-05"),
				User.GENDER.F,
				"Address dlgj sdfs sdf kj",
				"IDNUMBER7");
		User processedUser = processor.process(user);
		Assert.assertNull(processedUser);
	}

	@Test
	void shouldFailOnFutureDateOfBirth() throws Exception {
		User user = new User("+9999",
				User.SIM_TYPE.POSTPAID,
				"name",
				getDate("2028-05-05"),
				User.GENDER.F,
				"Address dlgj sdfs sdf kj",
				"IDNUMBER7");
		User processedUser = processor.process(user);
		Assert.assertNull(processedUser);
	}

	@Test
	void shouldFailOnAddressLessThan20Char() throws Exception {
		User user = new User("+9999",
				User.SIM_TYPE.POSTPAID,
				"name",
				getDate("2018-05-05"),
				User.GENDER.F,
				"Address dlgj",
				"IDNUMBER7");
		User processedUser = processor.process(user);
		Assert.assertNull(processedUser);
	}

	@Test
	void shouldFailOnIdNumberConstraint() throws Exception {
		User user = new User("+9999",
				User.SIM_TYPE.POSTPAID,
				"name",
				getDate("2018-05-05"),
				User.GENDER.F,
				"Address dlgj sdfs sdf kj",
				"IDNUMBER");
		User processedUser = processor.process(user);
		Assert.assertNull(processedUser);
	}

	@Test
	void shouldFailOnEmptySimCard() throws Exception {
		User user = new User("+9999",
				null,
				"name",
				getDate("2018-05-05"),
				User.GENDER.F,
				"Address dlgj sdfs sdf kj",
				"IDNUMBER7");
		User processedUser = processor.process(user);
		Assert.assertNull(processedUser);
	}

	@Test
	void shouldFailOnMsisdnCompliance() throws Exception {
		User user = new User("9999",
				User.SIM_TYPE.POSTPAID,
				"name",
				getDate("2018-05-05"),
				User.GENDER.F,
				"Address dlgj sdfs sdf kj",
				"IDNUMBER7");
		User processedUser = processor.process(user);
		Assert.assertNull(processedUser);
	}

	@Test
	void shouldFailOnDuplicateMsisdn() throws Exception {
		User user = new User("+8888",
				User.SIM_TYPE.POSTPAID,
				"name",
				getDate("2018-05-05"),
				User.GENDER.F,
				"Address dlgj sdfs sdf kj",
				"IDNUMBER7");
		User processedUser = processor.process(user);

		user = new User("+8888",
				User.SIM_TYPE.POSTPAID,
				"name2",
				getDate("2019-05-05"),
				User.GENDER.F,
				"Address cccc dddd eee fff",
				"IDNUMBER8");
		processedUser = processor.process(user);
		Assert.assertNull(processedUser);
	}

	private Date getDate(String dateStr){
		LocalDate localDate = LocalDate.parse(dateStr);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

}
