package org.application.bitrthdayreminder.Design;

import android.content.Context;

import org.application.bitrthdayreminder.Implementation.Model.Friend;
import org.application.bitrthdayreminder.Implementation.Model.Message;
import org.application.bitrthdayreminder.Implementation.Model.User;

import java.util.List;

/**
 * Created by sachin on 2/16/2016.
 */
public interface IService {

    public String store(Object object,Context context);
    public List<Object> retrieve(Object object,Context context);
    public String delete(Object object);
    public String edit(Object object);
}
