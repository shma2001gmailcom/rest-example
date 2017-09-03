package org.misha;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class UserDao {
    List<User> getAllUsers() {
        List<User> userList = null;
        try {
            File file = new File("Users.dat");
            System.out.println(file.getAbsolutePath());
            if (!file.exists()) {
                User user = new User(1, "misha", "misha");
                userList = new ArrayList<User>();
                userList.add(user);
                saveUserList(userList);
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                userList = (List<User>) ois.readObject();
                ois.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userList;
    }

    User getUser(int id) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    int addUser(User pUser) {
        List<User> userList = getAllUsers();
        boolean userExists = false;
        for (User user : userList) {
            if (user.getId() == pUser.getId()) {
                userExists = true;
                break;
            }
        }
        if (!userExists) {
            userList.add(pUser);
            saveUserList(userList);
            return 1;
        }
        return 0;
    }

    int updateUser(User pUser) {
        List<User> userList = getAllUsers();
        for (User user : userList) {
            if (user.getId() == pUser.getId()) {
                int index = userList.indexOf(user);
                userList.set(index, pUser);
                saveUserList(userList);
                return 1;
            }
        }
        return 0;
    }

    int deleteUser(int id) {
        List<User> userList = getAllUsers();
        for (User user : userList) {
            if (user.getId() == id) {
                int index = userList.indexOf(user);
                userList.remove(index);
                saveUserList(userList);
                return 1;
            }
        }
        return 0;
    }

    private void saveUserList(List<User> userList) {
        try {
            File file = new File("Users.dat");
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
