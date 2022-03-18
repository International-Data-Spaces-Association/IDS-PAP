import Submit from "../backend/Submit";
import {OdrlPolicy} from "../backend/OdrlPolicy"
function handleInputChange(e, valueHook) {
  const [values, setValues] = valueHook;
  setValues({ ...values, [e.target.name]: e.target.value });
}

function handleSubmit(e, valueHook, selected_components, setErrors, history) {
  const [values, setValues] = valueHook;

  Submit(
    "/policy/InformPolicyForm",
    values,
    selected_components,
    setErrors,
    history,
    e
  );
}

function handleClick(e, setAnchorEl) {
  setAnchorEl(e.currentTarget);
};

function handleClose(setAnchorEl){
  setAnchorEl(null);
};

function resetStates(setSelectedComponents, selected_components) {
  setSelectedComponents({ ...selected_components });
};

export { handleInputChange, handleSubmit,handleClick, handleClose, resetStates };
