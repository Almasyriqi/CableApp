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
import com.example.cableapp.databinding.FragmentLoginBinding;
import androidx.navigation.fragment.NavHostFragment;
import com.example.cableapp.viewModel.LoginViewModel;
import com.example.cableapp.R;

public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout_login.xml dengan Data Binding
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        FragmentLoginBinding binding = FragmentLoginBinding.bind(rootView);
        binding.setMyLoginViewModel(loginViewModel);
        binding.setLifecycleOwner(this);

        loginViewModel.getErrotoast().observe(getViewLifecycleOwner(), hasError -> {
            if (hasError == 1) {
                Toast.makeText(requireContext(), "Silahkan masukkan semua field", Toast.LENGTH_SHORT).show();
                loginViewModel.donetoast();
            } else if(hasError == 2){
                Toast.makeText(requireContext(), "Email yang dimasukkan salah", Toast.LENGTH_SHORT).show();
                loginViewModel.donetoast();
            } else if(hasError == 3){
                Toast.makeText(requireContext(), "User tidak ditemukan, silahkan register", Toast.LENGTH_SHORT).show();
                loginViewModel.donetoast();
            }
        });

        loginViewModel.getNavigatetoHome().observe(getViewLifecycleOwner(), hasFinished -> {
            if (hasFinished != null && hasFinished) {
                String email = binding.emailTextField.getText().toString();
                Log.i("MYTAG", "inside observe");
                Log.i("EMAIL", email);
                navigateHome(email);
                loginViewModel.doneNavigatingHome();
            }
        });

        loginViewModel.getNavigatetoRegister().observe(getViewLifecycleOwner(), hasFinished -> {
            if(hasFinished != null && hasFinished){
                Log.i("MYTAG", "navigasi ke register");
                navigateRegister();
                loginViewModel.doneNavigatingRegister();
            }
        });

        return rootView;
    }

    private void navigateHome(String email) {
        Log.i("MYTAG", "navigasi ke home");
        Toast.makeText(requireContext(), "Berhasil Login", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_homeFragment, bundle);
    }

    private void navigateRegister() {
        Log.i("MYTAG", "navigasi ke register");
        NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment);
    }
}
