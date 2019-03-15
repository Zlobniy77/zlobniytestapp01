package com.zlobniy.controller.client;

import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.client.entity.PhoneProperty;
import com.zlobniy.domain.client.service.ClientService;
import com.zlobniy.domain.client.view.ClientView;
import com.zlobniy.view.SimpleResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RestController
//@RequestMapping("/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PACKAGE)
final class SecuredUsersController {

    @NonNull
    ClientService clientService;

    @GetMapping( value = "/current")
    ClientView getCurrent( @RequestBody ClientView clientView ) {
        return clientView;
    }

    @RequestMapping( value = "/logout", method = RequestMethod.POST )
    SimpleResponse logout( @RequestBody ClientView clientView ) {

        clientService.logout( clientView.getId() );
        SimpleResponse response = new SimpleResponse();
        response.setMessage( "logout" );

        return response;
    }

    @RequestMapping( value = "/api/client/{id}/property", method = RequestMethod.GET )
    PhoneProperty loadProperty( @PathVariable Long id ) {

        Client client = clientService.find( id );
        if( client == null ) return new PhoneProperty();

        return client.getProperty();
    }

    @RequestMapping( value = "/api/client/{id}/property", method = RequestMethod.POST )
    SimpleResponse saveProperty( @PathVariable Long id, @RequestBody PhoneProperty property ) {

        Client client = clientService.find( id );
        if( client == null ){
            SimpleResponse response = new SimpleResponse();
            response.setMessage( "client not found" );
            return response;
        }

        client.setProperty( property );
        clientService.saveClient( client );

        SimpleResponse response = new SimpleResponse();
        response.setMessage( "success" );


        return response;
    }

}