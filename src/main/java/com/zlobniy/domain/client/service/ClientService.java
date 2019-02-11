package com.zlobniy.domain.client.service;

import com.zlobniy.domain.client.ClientDao;
import com.zlobniy.domain.client.entity.Client;
import com.zlobniy.domain.client.view.RegistrationView;
import com.zlobniy.domain.folder.entity.Folder;
import com.zlobniy.domain.folder.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService extends Client {

    private final ClientDao clientDao;
    private final FolderService folderService;

    @Autowired
    public ClientService( ClientDao clientDao, FolderService folderService ){
        this.clientDao = clientDao;
        this.folderService = folderService;
    }



    public Client login( final String username, final String password ) {
        final String uuid = UUID.randomUUID().toString();

        Client client = clientDao.findByCredentials( username, password );
        if( client != null ){
            client.setToken( uuid );
            clientDao.save( client );
            return client;
        }

        return null;
    }

    public Client createClient( RegistrationView registrationView ) {
        Client clientForm = new Client( registrationView );
        final String uuid = UUID.randomUUID().toString();
        clientForm.setToken( uuid );

        Client client = null;
        try {
            // change in save behaviour, in sprig boot save not working if column is unique and value already exist.
            // in spring boot v.2 that rule not working or not as before.
            if( clientDao.findByLogin( clientForm.getUsername() ) == null ){
                client = clientDao.save( clientForm );

                final Folder homeFolder = new Folder();
                homeFolder.setClient( client );
                homeFolder.setRoot( true );
                homeFolder.setSelected( true );
                homeFolder.setTitle( client.getUsername() );

                folderService.save( homeFolder );
                client.getFolders().add( homeFolder );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

       return client;
    }

    public Client saveClient( Client client ){
        return clientDao.save( client );
    }

    public Client findByToken(final String token) {
        return clientDao.findByToken( token );
    }

    public Client findWithFolders( Long id ){
        return clientDao.findWithFolders( id );
    }

    public void logout( final Long clientId ) {
        Client client = clientDao.findById( clientId ).get();
        client.setToken( null );
        clientDao.save( client );
    }

    public Client find( Long id ) {
        return clientDao.findById( id ).get();
    }

    public List<Client> findAll(){
        return clientDao.findAll();
    }
}