package com.example.cableapp.viewModel;

import android.app.Application;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cableapp.Repository;
import com.example.cableapp.database.UserEntity;

public class RegisterViewModel extends AndroidViewModel implements Observable {
    private Repository repository;
    private MutableLiveData<String> inputName = new MutableLiveData<>();
    private MutableLiveData<String> inputEmail = new MutableLiveData<>();
    private MutableLiveData<String> inputPassword = new MutableLiveData<>();
    private MutableLiveData<Boolean> _navigatetoLogin = new MutableLiveData<>();
    private LiveData<Boolean> navigatetoLogin = _navigatetoLogin;
    private MutableLiveData<Integer> _errorToast = new MutableLiveData<>();
    private LiveData<Integer> errotoast = _errorToast;

    public LiveData<Boolean> getNavigatetoLogin() {
        return navigatetoLogin;
    }

    public LiveData<Integer> getErrotoast() {
        return errotoast;
    }

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    @Bindable
    public MutableLiveData<String> getInputName() {
        return inputName;
    }

    @Bindable
    public MutableLiveData<String> getInputEmail() {
        return inputEmail;
    }

    @Bindable
    public MutableLiveData<String> getInputPassword() {
        return inputPassword;
    }

    public boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    public void registerButton() {
        if (inputName.getValue() == null || inputEmail.getValue() == null || inputPassword.getValue() == null) {
            _errorToast.setValue(1);
        } else if(isEmailValid(inputEmail.getValue()) == false){
            _errorToast.setValue(2);
        } else {
            UserEntity user = new UserEntity();
            user.setEmail(inputEmail.getValue());
            user.setUsername(inputName.getValue());
            user.setPassword(inputPassword.getValue());
            repository.insertUser(user);
            _navigatetoLogin.postValue(true);
        }
    }

    public void toLoginPage() {
        _navigatetoLogin.setValue(true);
    }

    public void doneNavigatingLogin() {
        _navigatetoLogin.setValue(false);
    }

    public void donetoast() {
        _errorToast.setValue(0);
        Log.i("MYTAG", "Done taoasting ");
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }
}
