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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginViewModel extends AndroidViewModel implements Observable {
    private Repository repository;
    private ExecutorService executorService;
    private MutableLiveData<String> inputEmail = new MutableLiveData<>();
    private MutableLiveData<String> inputPassword = new MutableLiveData<>();
    private MutableLiveData<Boolean> _navigatetoHome = new MutableLiveData<>();
    private LiveData<Boolean> navigatetoHome = _navigatetoHome;

    private MutableLiveData<Boolean> _navigatetoRegister = new MutableLiveData<>();
    private LiveData<Boolean> navigatetoRegister = _navigatetoRegister;
    private MutableLiveData<Integer> _errorToast = new MutableLiveData<>();
    private LiveData<Integer> errotoast = _errorToast;

    public LiveData<Boolean> getNavigatetoHome() {
        return navigatetoHome;
    }

    public LiveData<Boolean> getNavigatetoRegister() {
        return navigatetoRegister;
    }

    public LiveData<Integer> getErrotoast() {
        return errotoast;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    @Bindable
    public MutableLiveData<String> getInputEmail() {
        return inputEmail;
    }

    @Bindable
    public MutableLiveData<String> getInputPassword() {
        return inputPassword;
    }

    // A placeholder email validation check
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

    public void loginButton() {

        if (inputEmail.getValue() == null || inputPassword.getValue() == null) {
            _errorToast.setValue(1);
        } else if(isEmailValid(inputEmail.getValue()) == false){
            _errorToast.setValue(2);
        } else {
            executorService.execute(()->{
                Log.i("EMAIL", inputEmail.getValue());
                UserEntity user = repository.login(inputEmail.getValue(), inputPassword.getValue());
                if (user != null) {
                    inputEmail.postValue(null);
                    inputPassword.postValue(null);
                    _navigatetoHome.postValue(true);
                } else {
                    _errorToast.postValue(3);
                }
            });
        }
    }

    public void toRegisterPage(){
        _navigatetoRegister.setValue(true);
    }

    public void doneNavigatingHome() {
        _navigatetoHome.setValue(false);
    }
    public void doneNavigatingRegister() {
        _navigatetoRegister.setValue(false);
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
