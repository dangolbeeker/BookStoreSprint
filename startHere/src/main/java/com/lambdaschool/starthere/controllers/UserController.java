package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Author;
import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.models.User;
import com.lambdaschool.starthere.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "returns all users", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All users returned", responseContainer ="List", response = User.class),
            @ApiResponse(code = 404, message = "All users not found", responseContainer ="List", response = User.class),
            @ApiResponse(code = 500, message = "Error retrieving users", responseContainer ="List", response =
                    User.class)
    })
    @GetMapping(value = "/users",
                produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(HttpServletRequest request)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "returns user by id", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Uusers returned by id", responseContainer ="List", response =
                    User.class),
            @ApiResponse(code = 404, message = "User not found", responseContainer ="List", response = User.class),
            @ApiResponse(code = 500, message = "Error retrieving user", responseContainer ="List", response =
                    User.class)
    })
    @GetMapping(value = "/user/{userId}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserById(HttpServletRequest request,
                                         @PathVariable
                                                 Long userId)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "returns user by username", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Username returned", responseContainer ="List", response = User.class),
            @ApiResponse(code = 404, message = "user not found", responseContainer ="List", response = User.class),
            @ApiResponse(code = 500, message = "Error retrieving user", responseContainer ="List", response =
                    User.class)
    })
    @GetMapping(value = "/user/name/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserByName(HttpServletRequest request,
                                           @PathVariable
                                                   String userName)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        User u = userService.findByName(userName);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }


    @ApiOperation(value = "returns current user", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Username returned", responseContainer ="List", response = User.class),
            @ApiResponse(code = 404, message = "Username not found", responseContainer ="List", response = User.class),
            @ApiResponse(code = 500, message = "Error retrieving username", responseContainer ="List", response =
                    User.class)
    })
    @GetMapping(value = "/getusername",
                produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUserName(HttpServletRequest request, Authentication authentication)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        return new ResponseEntity<>(authentication.getPrincipal(), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "adds new user", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "New user added", responseContainer ="List", response = User.class),
            @ApiResponse(code = 404, message = "New user unable to be added", responseContainer ="List", response =
                    User.class),
            @ApiResponse(code = 500, message = "Error adding user", responseContainer ="List", response =
                    User.class)
    })
    @PostMapping(value = "/user",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest request, @Valid
    @RequestBody
            User newuser) throws URISyntaxException
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{userid}")
                                                    .buildAndExpand(newuser.getUserid())
                                                    .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "updates a user", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated", responseContainer ="List", response = User.class),
            @ApiResponse(code = 404, message = "User not found", responseContainer ="List", response =
                    User.class),
            @ApiResponse(code = 500, message = "Error updating user", responseContainer ="List", response =
                    User.class)
    })
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @RequestBody
                                                User updateUser,
                                        @PathVariable
                                                long id)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.update(updateUser, id, request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Deletes a user", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted", responseContainer ="List", response = User.class),
            @ApiResponse(code = 404, message = "User not found", responseContainer ="List", response =
                    User.class),
            @ApiResponse(code = 500, message = "Error deleting user", responseContainer ="List", response =
                    User.class)
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(HttpServletRequest request,
                                            @PathVariable
                                                    long id)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiOperation(value = "Deletes user roles by ID", response = User.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User roles deleted", responseContainer ="List", response = User.class),
            @ApiResponse(code = 404, message = "User or roles not found", responseContainer ="List", response =
                    User.class),
            @ApiResponse(code = 500, message = "Error deleting user roles", responseContainer ="List", response =
                    User.class)
    })
    @DeleteMapping("/user/{userid}/role/{roleid}")
    public ResponseEntity<?> deleteUserRoleByIds(HttpServletRequest request,
                                                 @PathVariable
                                                         long userid,
                                                 @PathVariable
                                                         long roleid)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.deleteUserRole(userid, roleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/user/{userid}/role/{roleid}")
    public ResponseEntity<?> postUserRoleByIds(HttpServletRequest request,
                                               @PathVariable
                                                       long userid,
                                               @PathVariable
                                                       long roleid)
    {
        logger.trace(request.getMethod()
                            .toUpperCase() + " " + request.getRequestURI() + " accessed");

        userService.addUserRole(userid, roleid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}