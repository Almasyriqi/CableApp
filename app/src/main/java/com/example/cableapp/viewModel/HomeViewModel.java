package com.example.cableapp.viewModel;

import android.app.Application;
import android.util.Log;

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

public class HomeViewModel extends AndroidViewModel implements Observable {
    private Repository repository;
    private ExecutorService executorService;
    private MutableLiveData<String> _username = new MutableLiveData<>();
    private LiveData<String> username = _username;
    private MutableLiveData<String> inputLength = new MutableLiveData<>();
    private MutableLiveData<Boolean> _navigatetoLogin = new MutableLiveData<>();
    private LiveData<Boolean> navigatetoLogin = _navigatetoLogin;
    private MutableLiveData<Integer> _errorToast = new MutableLiveData<>();
    private LiveData<Integer> errotoast = _errorToast;
    private MutableLiveData<Integer> _successToast = new MutableLiveData<>();
    private LiveData<Integer> successtoast = _successToast;

    public LiveData<Integer> getSuccesstoast() {
        return successtoast;
    }

    public LiveData<Integer> getErrotoast() {
        return errotoast;
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public LiveData<Boolean> getNavigatetoLogin() {
        return navigatetoLogin;
    }

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        executorService = Executors.newSingleThreadExecutor();
    }

    @Bindable
    public MutableLiveData<String> getInputLength() {
        return inputLength;
    }

    public void getUserData(String email) {
        executorService.execute(() -> {
            Log.i("EMAIL_USER", email);
            UserEntity user = repository.getEmail(email);
            if (user != null) {
                _username.postValue(user.getUsername());
            } else {
                _username.postValue("User tidak ditemukan");
            }
        });
    }

    public void addCable() {
        if (inputLength.getValue() == null) {
            _errorToast.setValue(1);
        } else {
            int cableLength = Integer.parseInt(inputLength.getValue());
            repository.insertCable(cableLength);
            _successToast.setValue(1);
        }
    }

    public void logoutButton() {
        _navigatetoLogin.setValue(true);
    }

    public void doneLogout() {
        _navigatetoLogin.setValue(false);
        _username.setValue(null);
    }

    public void donetoast() {
        _errorToast.setValue(0);
        _successToast.setValue(0);
        Log.i("MYTAG", "Done taoasting ");
    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {

    }
}
