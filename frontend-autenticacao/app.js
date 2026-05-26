const form = document.querySelector("#loginForm");
const message = document.querySelector("#message");
const submitButton = document.querySelector("#submitButton");
const apiStatus = document.querySelector("#apiStatus");
const sessionBox = document.querySelector("#sessionBox");
const tokenPreview = document.querySelector("#tokenPreview");
const logoutButton = document.querySelector("#logoutButton");

const setMessage = (text, type = "") => {
  message.textContent = text;
  message.className = `message ${type}`.trim();
};

const setSession = (token) => {
  if (!token) {
    localStorage.removeItem("clinica.auth.token");
    sessionBox.hidden = true;
    tokenPreview.textContent = "";
    return;
  }

  localStorage.setItem("clinica.auth.token", token);
  sessionBox.hidden = false;
  tokenPreview.textContent = token;
};

const checkApi = async () => {
  try {
    const response = await fetch("/api/auth/health");
    if (!response.ok) throw new Error("API indisponivel");
    apiStatus.textContent = "online";
    apiStatus.className = "status-pill ok";
  } catch {
    apiStatus.textContent = "offline";
    apiStatus.className = "status-pill offline";
  }
};

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  submitButton.disabled = true;
  setMessage("Autenticando...");

  const formData = new FormData(form);
  const payload = {
    email: formData.get("email"),
    senha: formData.get("senha"),
  };

  try {
    const response = await fetch("/api/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    const data = await response.json();
    if (!response.ok) {
      throw new Error(data.erro || "Falha no login");
    }

    setSession(data.token);
    setMessage("Login realizado com sucesso.", "success");
  } catch (error) {
    setSession(null);
    setMessage(error.message, "error");
  } finally {
    submitButton.disabled = false;
  }
});

logoutButton.addEventListener("click", () => {
  setSession(null);
  setMessage("Sessao encerrada.");
});

setSession(localStorage.getItem("clinica.auth.token"));
checkApi();
