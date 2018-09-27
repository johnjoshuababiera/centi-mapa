package com.usa.centimapa.packages;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageRepository repository;

    @Override
    public Package findOne(long id) {
        return repository.findById(id).get();
    }

    @Override
    public Package save(Package pkg) throws Exception {
        Package duplicatePkg = repository.findByName(pkg.getName());
        if(duplicatePkg!=null && duplicatePkg.getId()!=pkg.getId()){
            throw new Exception("Duplicate Package!");
        }
        return repository.save(pkg);
    }

    @Override
    public void remove(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Package> findAll() {
        List<Package> packages = new ArrayList<>();
        repository.findAll().forEach(pkg->packages.add(pkg));
        return packages;
    }
}
