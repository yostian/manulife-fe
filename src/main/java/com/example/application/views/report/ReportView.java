package com.example.application.views.report;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Menu(title = "Report", order = 2, icon = "path/to/icon.svg")
@PageTitle("User Report")
@Route("report")
@PermitAll
public class ReportView extends VerticalLayout {

    private final Select<String> userTypeDropdown = new Select<>();
    private final Button downloadButton = new Button("Download PDF");

    public ReportView() {
        setSizeFull();

        userTypeDropdown.setLabel("Pilih Jenis User");
        userTypeDropdown.setItems("Semua", "Reguler", "VIP", "VVIP");
        userTypeDropdown.setValue("Semua"); // Set default selection

        downloadButton.addClickListener(e -> {
            String userType = userTypeDropdown.getValue();
            String downloadUrl = "Semua".equals(userType)
                ? "http://localhost:8082/api/reports/users/pdf"
                : "http://localhost:8082/api/reports/users/pdf?jenisUser=" + userType;

            // Buka link di tab baru
            getUI().ifPresent(ui -> ui.getPage().open(downloadUrl, "_blank"));

            // Beri notifikasi bahwa file sedang diunduh
            Notification.show("Mengunduh laporan...", 2000, Notification.Position.MIDDLE);
        });

        add(userTypeDropdown, downloadButton);
    }
}
