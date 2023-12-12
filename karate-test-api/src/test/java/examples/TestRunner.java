package examples;

import com.intuit.karate.junit5.Karate;

class TestRunner {

    @Karate.Test
    Karate testUsers() {
        return Karate.run("authentication_service/user")
                .relativeTo(getClass());
    }

}
