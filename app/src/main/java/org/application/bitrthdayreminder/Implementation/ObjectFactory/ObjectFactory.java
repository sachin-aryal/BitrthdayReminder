package org.application.bitrthdayreminder.Implementation.ObjectFactory;

import org.application.bitrthdayreminder.Design.IService;
import org.application.bitrthdayreminder.Implementation.Service.FriendsService;
import org.application.bitrthdayreminder.Implementation.Service.MessageService;
import org.application.bitrthdayreminder.Implementation.Service.UserService;

/**
 * Created by sachin on 2/16/2016.
 */
public class ObjectFactory {
    public IService getServiceObject(String objectType){
        if(objectType.equals("friends")){
            return new FriendsService();
        }else if(objectType.equals("message")){
            return new MessageService();
        }else if(objectType.equals("user")){
            new UserService();
        }
        return null;
    }
}
