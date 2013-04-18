package 单测;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserDaoTest1.class, UserDaoTest2.class })
public class UserDaoTests {

}
