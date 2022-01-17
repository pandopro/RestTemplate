package RestTemplate.service;

import RestTemplate.model.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FakeService {
    private ArrayList<User> userArrayList = new ArrayList<>();

    public User get(long id) {
        return userArrayList.get((int) id);
    }

    public boolean add(User user) {
        return userArrayList.add(user);
    }

    public boolean delete(long id) {
        userArrayList.remove((int) id);
        return true;
    }

    public ArrayList<User> getAll() {
        return userArrayList;
    }
    public boolean AddAll(String data) {
        Gson gson = new Gson();
        User[] users = gson.fromJson(data, User[].class);
        for (int i = 0; i < users.length; i++) {
            System.out.println(i + ") " + users[i].toString());
            userArrayList.add(users[i]);
        }

        return  true;
    }
}
