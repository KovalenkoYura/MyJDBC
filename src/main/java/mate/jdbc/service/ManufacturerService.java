package mate.jdbc.service;

import mate.jdbc.entity.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {
  Manufacturer save(Manufacturer manufacturer);

  Optional<Manufacturer> get(Long id);

  List<Manufacturer> getAll();

  Manufacturer update(Manufacturer manufacturer);

  boolean delete(Long id);
}
