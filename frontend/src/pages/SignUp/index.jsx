import { useEffect, useState } from "react";
import Spinner from "react-bootstrap/Spinner";
import { signUp } from "./api";

export function SignUp() {
  const [username, setUsername] = useState();
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [passwordRepeat, setPasswordRepeat] = useState();
  const [apiProgress, setApiProgress] = useState(false);
  const [successMessage, setSuccessMessage] = useState();
  const [errors, setErrors] = useState({});
  const [generalError, setGeneralError] = useState();
  useEffect(() => {
    setErrors({});
  }, [username]);

  const onSubmit = async (e) => {
    e.preventDefault(); //enter'a basıldığında form ile beraber sayfanın tekrar yüklenmesini engellemek için kullanılır
    setSuccessMessage();
    setGeneralError();
    setApiProgress(true);

    try {
      const response = await signUp({
        username,
        email,
        password,
      });
      setSuccessMessage(response.data.message);
    } catch (axiosError) {
      if (
        axiosError.response?.data &&
        axiosError.response.data.status === 400
      ) {
        setErrors(axiosError.response.data.validationErrors);
      } else{
        setGeneralError("Unexpected error occured. Please try again");
      }
    } finally {
      setApiProgress(false);
    }

    /*.then((response) => {
        setSuccessMessage(response.data.message);
      })
      .finally(() => setApiProgress(false));*/
  };

  return (
    <div className=" container d-flex align-items-center justify-content-center vh-100">
      <div style={{ width: "30rem" }}>
        <form onSubmit={onSubmit} className="card">
          <div className="text-center card-header">
            <h1>Sign Up</h1>
          </div>
          <div className="card-body">
            <div className="mb-3">
              <label htmlFor="username" className="form-label">
                Username
              </label>
              <input
                className={
                  errors.username ? "form-control is-invalid" : "form-control"
                }
                type="text"
                name="username"
                id="username"
                onChange={(e) => setUsername(e.target.value)}
              />
              <div className="invalid-feedback">{errors.username}</div>
            </div>

            <div className="mb-3">
              <label htmlFor="email" className="form-label">
                Email
              </label>
              <input
                className="form-control"
                type="email"
                name="email"
                id="email"
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                Password
              </label>
              <input
                className="form-control"
                type="password"
                name="password"
                id="password"
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="passwordRepeat" className="form-label">
                Confirm Password
              </label>
              <input
                type="password"
                className="form-control"
                name="passwordRepeat"
                id="passwordRepeat"
                onChange={(e) => setPasswordRepeat(e.target.value)}
              />
            </div>
            {successMessage && (
              <div className="alert alert-success">{successMessage}</div>
            )}
             {generalError && (
              <div className="alert alert-danger">{generalError}</div>
            )}
            <div className="text-center">
              <button
                className="btn btn-primary"
                type="submit"
                disabled={
                  apiProgress || !password || password !== passwordRepeat
                }
              >
                {apiProgress ? (
                  <Spinner animation="border" role="status">
                    <span className="visually-hidden">Submited</span>
                  </Spinner>
                ) : (
                  "Submit"
                )}
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
