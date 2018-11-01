package br.com.job.controller;

import br.com.job.exception.NotFoundException;
import br.com.job.exception.UnauthorizedException;
import br.com.job.model.User;
import br.com.job.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/owner")
public class UserController {

    @Autowired
    UserRepository ownerRepository;

    @GetMapping("/all")
    public List<User> getAllOwners() {
        List<User> owners =  ownerRepository.findAll();
        for(User owner : owners) {
        	owner.setPassword(null);
        }
        return owners;
    }

    @PostMapping("")
    public User createOwner(@Valid @RequestBody User newOwner, @RequestHeader(value="ownerId") Integer ownerId,
    		@RequestHeader(value="username") String usr,
    		@RequestHeader(value="password") String pwd) {
    	Optional<User> owner = ownerRepository.findById(ownerId);
    	if(owner.get() == null || 
    			!owner.get().getUsername().equals(usr) ||
    			!owner.get().getPassword().equals(pwd)) {
    		throw new UnauthorizedException("/owner", "id", ownerId );
    	}
        return ownerRepository.save(newOwner);
    }

    @GetMapping("/{id}")
    public User getOwnerById(@PathVariable(value = "id") Integer id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Owner", "id", id));
    }
 
}
