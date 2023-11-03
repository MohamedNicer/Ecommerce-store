package com.ecs.ecommercestore.Api.Controller.User;

import com.ecs.ecommercestore.Api.Model.DataChange;
import com.ecs.ecommercestore.Entity.Address;
import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Repository.AddressRepository;
import com.ecs.ecommercestore.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Rest Controller for user data interactions.
 * @author mohamednicer
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /** The Address Repository. */
    private AddressRepository addressRepository;
    /** The simpMessageTemplate that would allow us to use WebSockets in our endpoints */
    private SimpMessagingTemplate simpMessagingTemplate;
    private UserService userService;

    /**
     * Constructor for spring injection.
     *
     * @param addressRepository
     * @param simpMessagingTemplate
     * @param userService
     */
    public UserController(AddressRepository addressRepository, SimpMessagingTemplate simpMessagingTemplate, UserService userService) {
        this.addressRepository = addressRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
    }

    /**
     * Gets all addresses for the given user and presents them.
     * @param user The authenticated user account.
     * @param userId The user ID to get the addresses of.
     * @return The list of addresses.
     */
    @GetMapping("/{userId}/address")
    public ResponseEntity<List<Address>> getUserAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Long userId){
        if(!userService.userHasPermissionToUser(user, userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(addressRepository.findByUser_Id(userId));
    }

    /**
     * Allows the user to add a new address.
     * @param user The authenticated user.
     * @param userId The user id for the new address.
     * @param address The Address to be added.
     * @return The saved address.
     */
    @PutMapping("/{userId}/address")
    public ResponseEntity<Address> putAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Long userId, @RequestBody Address address){
        if(!userService.userHasPermissionToUser(user, userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        address.setId(null);
        LocalUser refUser = new LocalUser();
        refUser.setId(userId);
        address.setUser(refUser);
        Address savedAddress = addressRepository.save(address);
        simpMessagingTemplate.convertAndSend("/topic/user/" + userId + "/address",
                new DataChange<>(DataChange.ChangeType.INSERT,address));
        return ResponseEntity.ok(savedAddress);
    }

    /**
     * Updates the given address.
     * @param user The authenticated user.
     * @param userId The user ID the address belongs to.
     * @param addressId The address ID to alter.
     * @param address The updated address object.
     * @return The saved address object.
     */
    @PatchMapping("{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Long userId,
            @PathVariable Long addressId, @RequestBody Address address){
        if(!userService.userHasPermissionToUser(user, userId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (Objects.equals(address.getId(), addressId)){
            Optional<Address> optionalAddress = addressRepository.findById(addressId);
            if (optionalAddress.isPresent()){
                LocalUser localUser = optionalAddress.get().getUser();
                    if (Objects.equals(localUser.getId(), userId)){
                        address.setUser(localUser);
                        Address savedAddress = addressRepository.save(address);
                        simpMessagingTemplate.convertAndSend("/topic/user/" + userId + "/address",
                                new DataChange<>(DataChange.ChangeType.UPDATE,address));
                        return ResponseEntity.ok(savedAddress);
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
