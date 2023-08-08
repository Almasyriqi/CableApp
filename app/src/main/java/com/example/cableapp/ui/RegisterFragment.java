package com.example.cableapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cableapp.R;
import com.example.cableapp.databinding.FragmentRegisterBinding;
import com.example.cableapp.viewModel.RegisterViewModel;

public class RegisterFragment extends Fragment {
    private RegisterViewModel registerViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        FragmentRegisterBinding binding = FragmentRegisterBinding.bind(rootView);
        binding.setMyRegisterViewModel(registerViewModel);
        binding.setLifecycleOwner(this);

        registerViewModel.getErrotoast().observe(getViewLifecycleOwner(), hasError -> {
            if (hasError == 1) {
                Toast.makeText(requireContext(), "Silahkan masukkan semua field", Toast.LENGTH_SHORT).show();
                registerViewModel.donetoast();
            } else if(hasError == 2){
                Toast.makeText(requireContext(), "Email yang dimasukkan salah", Toast.LENGTH_SHORT).show();
                registerViewModel.donetoast();
            }
        });

        registerViewModel.getNavigatetoLogin().observe(getViewLifecycleOwner(), hasFinished -> {
            if (hasFinished != null && hasFinished) {
                Log.i("MYTAG", "inside observe");
                navigateLogin();
                registerViewModel.doneNavigatingLogin();
            }
        });

        return rootView;
    }

    private void navigateLogin() {
        Log.i("MYTAG", "navigasi ke login");
        NavHostFragment.findNavController(this).navigate(R.id.action_registerFragment_to_loginFragment);
    }
}
