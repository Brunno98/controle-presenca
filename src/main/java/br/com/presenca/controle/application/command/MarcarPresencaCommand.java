package br.com.presenca.controle.application.command;

public class MarcarPresencaCommand {
    private String userId;
    private String atividadeId;

    public MarcarPresencaCommand(String userId, String atividadeId) {
        this.userId = userId;
        this.atividadeId = atividadeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAtividadeId() {
        return atividadeId;
    }

    public void setAtividadeId(String atividadeId) {
        this.atividadeId = atividadeId;
    }
}
