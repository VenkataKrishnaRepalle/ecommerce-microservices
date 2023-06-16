package com.spring6.ecommerce.bootstrap;

import com.spring6.ecommerce.entity.Brand;
import com.spring6.ecommerce.common.dto.brand.BrandStatusEnum;
import com.spring6.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
@RequiredArgsConstructor
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
                    .logo("Acer.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Samsung")
                    .logo("Samsung.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Canon")
                    .logo("Canon.png")
                    .status(BrandStatusEnum.INACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("TVS")
                    .logo("tvs.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Honda")
                    .logo("honda.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Apple")
                    .logo("apple.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Xiomi")
                    .logo("xiomi.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("mi")
                    .logo("mi.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Philips")
                    .logo("philps.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Voltas")
                    .logo("voltas.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Pixma")
                    .logo("pixma.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            brandRepository.save(Brand.builder()
                    .name("Singer")
                    .logo("singer.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());
        }
    }
}
