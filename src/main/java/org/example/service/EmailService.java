package org.example.service;

import org.example.model.Greeting;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by sergeikostin on 3/4/16.
 */

public interface EmailService {

    boolean send(Greeting greeting); //send synchronously on main thread
    void sendAsync(Greeting greeting);
    Future<Boolean> sendAsyncForResult(Greeting greeting); //Future interface provides methods to async processes which allow them to return value or exception

}
