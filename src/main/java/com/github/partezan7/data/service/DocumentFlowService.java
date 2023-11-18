package com.github.partezan7.data.service;

import com.github.partezan7.data.entity.Company;
import com.github.partezan7.data.entity.Contact;
import com.github.partezan7.data.entity.Status;
import com.github.partezan7.data.repository.CompanyRepository;
import com.github.partezan7.data.repository.ContactRepository;
import com.github.partezan7.data.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 
public class DocumentFlowService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;

    public DocumentFlowService(ContactRepository contactRepository,
                               CompanyRepository companyRepository,
                               StatusRepository statusRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
    }

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) { 
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        if (contact == null) { 
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }
}