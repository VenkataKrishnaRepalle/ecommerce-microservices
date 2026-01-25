package com.pm.spring.ema.brand.bootstrap;

import com.pm.spring.ema.brand.model.entity.Brand;
import com.pm.spring.ema.brand.model.repository.BrandRepository;
import com.pm.spring.ema.common.util.enums.BrandStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {
    private final BrandRepository brandRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBrandData();
    }

    private void loadBrandData() {
        if (brandRepository.count() == 0) {
            brandRepository.save(Brand.builder()
                    .name("Acer")
                    .imageName("Acer.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Samsung")
                    .imageName("Samsung.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Canon")
                    .imageName("Canon.png")
                    .status(BrandStatusEnum.INACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("TVS")
                    .imageName("tvs.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Honda")
                    .imageName("honda.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Apple")
                    .imageName("apple.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Xiomi")
                    .imageName("xiomi.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("mi")
                    .imageName("mi.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Philips")
                    .imageName("philps.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Voltas")
                    .imageName("voltas.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Pixma")
                    .imageName("pixma.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Singer")
                    .imageName("singer.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());
        }
    }
}
