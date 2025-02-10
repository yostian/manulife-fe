package com.example.application.views.User;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.example.application.model.User;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Menu(title = "User", order = 1, icon = "path/to/icon.svg")
@PageTitle("User")
@Route("")
@PermitAll
public class UserView extends VerticalLayout {

    private Grid<User> grid = new Grid<>(User.class);
    private TextField nameField = new TextField("Nama");
    private Select<String> jenisUserSelect = new Select<>(); // Ganti TextField menjadi Select
    private TextField alamatField = new TextField("Alamat");
    private Button addButton = new Button("Add User");

    private User selectedUser = null;

    private final RestTemplate restTemplate;
    private static final String API_URL = "http://localhost:8082/api/user";

    @Autowired
    public UserView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        setSizeFull();

        // Konfigurasi dropdown Jenis User
        jenisUserSelect.setLabel("Jenis User");
        jenisUserSelect.setItems("Reguler", "VIP", "VVIP");
        jenisUserSelect.setValue("Reguler"); // Set default value

        // Form input
        HorizontalLayout formLayout = new HorizontalLayout(nameField, jenisUserSelect, alamatField, addButton);
        formLayout.setAlignItems(Alignment.BASELINE);

        addButton.addClickListener(e -> {
            if (selectedUser == null) {
                addUser();
            } else {
                updateUser();
            }
        });
        addButton.addClickShortcut(Key.ENTER);

        // Grid untuk daftar user
        grid.setColumns("id", "nama", "jenisUser", "alamat");
        grid.addColumn(user -> user.getCreateDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                .setHeader("Created Date");

        grid.addComponentColumn(user -> {
            Button editButton = new Button("Edit", e -> editUser(user));
            Button deleteButton = new Button("Delete", e -> deleteUser(user));
            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Actions");

        updateGrid();

        // Tambahkan form dan grid
        add(formLayout, grid);
    }

    private void updateGrid() {
        try {
            User[] users = restTemplate.getForObject(API_URL, User[].class);
            grid.setItems(users != null ? Arrays.asList(users) : List.of());
        } catch (Exception e) {
            Notification.show("Gagal mengambil data dari backend!", 3000, Notification.Position.MIDDLE);
        }
    }

    private void addUser() {
        if (nameField.isEmpty() || jenisUserSelect.isEmpty() || alamatField.isEmpty()) {
            Notification.show("Nama, Jenis User, dan Alamat tidak boleh kosong!", 3000, Notification.Position.MIDDLE);
            return;
        }

        User user = new User(nameField.getValue(), jenisUserSelect.getValue(), alamatField.getValue());
        try {
            restTemplate.postForObject(API_URL, user, User.class);
            updateGrid();
            nameField.clear();
            jenisUserSelect.setValue("Reguler"); // Reset ke default
            alamatField.clear();
        } catch (Exception e) {
            Notification.show("Gagal menambahkan user!", 3000, Notification.Position.MIDDLE);
        }
    }

    private void editUser(User user) {
        selectedUser = user;
        nameField.setValue(user.getNama());
        jenisUserSelect.setValue(user.getJenisUser()); // Atur dropdown dengan nilai yang dipilih
        alamatField.setValue(user.getAlamat());
        addButton.setText("Update User");
    }

    private void updateUser() {
        if (nameField.isEmpty() || jenisUserSelect.isEmpty() || alamatField.isEmpty()) {
            Notification.show("Nama, Jenis User, dan Alamat tidak boleh kosong!", 3000, Notification.Position.MIDDLE);
            return;
        }

        selectedUser.setNama(nameField.getValue());
        selectedUser.setJenisUser(jenisUserSelect.getValue()); // Gunakan nilai dari dropdown
        selectedUser.setAlamat(alamatField.getValue());

        try {
            restTemplate.put(API_URL + "/" + selectedUser.getId(), selectedUser);
            updateGrid();
            nameField.clear();
            jenisUserSelect.setValue("Reguler"); // Reset ke default
            alamatField.clear();
            addButton.setText("Add User");
            selectedUser = null;
        } catch (Exception e) {
            Notification.show("Gagal mengupdate user!", 3000, Notification.Position.MIDDLE);
        }
    }

    private void deleteUser(User user) {
        try {
            restTemplate.delete(API_URL + "/" + user.getId());
            updateGrid();
        } catch (Exception e) {
            Notification.show("Gagal menghapus user!", 3000, Notification.Position.MIDDLE);
        }
    }
}
