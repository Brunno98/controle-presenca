package br.com.presenca.controle.infraestructure.controller.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

public record MarcarPresencaRequest(
        String atividadeId
) {}
