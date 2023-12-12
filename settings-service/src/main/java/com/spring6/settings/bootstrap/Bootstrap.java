package com.pm.spring.ema.settings.bootstrap;

import com.pm.spring.ema.settings.entity.Settings;
import com.pm.spring.ema.common.enums.SettingCategory;
import com.pm.spring.ema.settings.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final SettingsRepository settingsRepository;


    @Override
    public void run(String... args) throws Exception {
        loadCategoryAndSubCategoryData();
    }

    private void loadCategoryAndSubCategoryData() {
        if (settingsRepository.count() == 0) {
            settingsRepository.save(Settings.builder()
                    .keyName("SITE_NAME")
                    .valueField("Shop_me")
                    .category(SettingCategory.GENERAL)
                    .build());
            settingsRepository.save(Settings.builder()
                    .keyName("SITE_LOGO")
                    .valueField("Shopme.png")
                    .category(SettingCategory.GENERAL)
                    .build());
            settingsRepository.save(Settings.builder()
                    .keyName("COPYRIGHT")
                    .valueField("Copyright(C) 2023 shopeme Ltd.")
                    .category(SettingCategory.GENERAL)
                    .build());
            settingsRepository.save(Settings.builder()
                    .keyName("CURRENCY_ID")
                    .valueField("1")
                    .category(SettingCategory.CURRENCY)
                    .build());
            settingsRepository.save(Settings.builder()
                    .keyName("CURRENCY_SYMBOL")
                    .valueField("$")
                    .category(SettingCategory.CURRENCY)
                    .build());
            settingsRepository.save(Settings.builder()
                    .keyName("CURRENCY_SYMBOL_POSITION")
                    .valueField("Before Price")
                    .category(SettingCategory.CURRENCY)
                    .build());
            settingsRepository.save(Settings.builder()
                    .keyName("DECIMAL_POINT_TYPE")
                    .valueField("POINT")
                    .category(SettingCategory.CURRENCY)
                    .build());
            settingsRepository.save(Settings.builder()
                    .keyName("DECIMAL_DIGITS")
                    .valueField("2")
                    .category(SettingCategory.CURRENCY)
                    .build());
            settingsRepository.save(Settings.builder()
                    .keyName("THOUSANDS_POINT_TYPE")
                    .valueField("COMMA")
                    .category(SettingCategory.CURRENCY)
                    .build());


//        }
//        if (currencyRepository.count() == 0){
//            currencyRepository.save(Currency.builder()
//                            .name("United States Dollar")
//                            .symbol("$")
//                            .code("USD")
//                    .build());
        }

    }
}
