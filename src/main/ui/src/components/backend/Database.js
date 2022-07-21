/**
 * @file Manages the connection with the saved policies in the backend.
 * @author Tom Kollmer 
 */

import axios from "axios";

/**
 * Tries to connect to the backend to retrieve all stored template policies.
 * The received data is then added to the data object using the setData hook.
 * @param {func} setData react hook to change the value of the data object.
 */
function getAllPolicies(setData) {
  axios.get( "/api/policies").then(
    (response) => {
      console.log(response.data)
      setData(response.data);
    },
    (error) => {
      console.log(error);
    }
  );
}

/**
 * Tries to connect to the backend to download the policy with the specified ID.
 * The policy is then converted to a json file and downloaded automatically.
 * @param {number} id the id of the required policy
 * @param {string} fileName filename of the created file
 * @param {string} contentType flag to specify the file type. Plain json "text/plain"
 */
function exportPolicy(id, fileName, contentType) {
  axios.get(`/api/policies/export_${id}`).then(
    (response) => {
      const a = document.createElement("a");
      const file = new Blob([JSON.stringify(response.data, null, 2)], {
        type: contentType,
      });
      a.href = URL.createObjectURL(file);
      a.download = fileName;
      a.click();
    },
    (error) => {
      console.log(error);
    }
  );
}

/**
 * Tries to connect with the backend to delete the saved policy with the given ID.
 * If the delete process was successful, all remaining policies are retrieved.
 * @param {number} id the id of the policy that should be deleted
 * @param {object} data object that contains a local copy of the saved policies
 * @param {func} setData react hook to change the value of the data object.
 */
function deletePolicy(id, data, setData) {
  axios.delete( `/api/policies/${id}`).then(
    (response) => {
      getAllPolicies(data, setData);
    },
    (error) => {
      console.log(error);
    }
  );
}

/**
 * Tries to connect with the backend to retrieve the given policy.
 * If the operation was successful, the page where the policy was created
 * is opened and all values from the template policy are pre-filled in the 
 * text fields.
 * @param {number} id the id of the policy that should be deleted
 * @param {object} history object that contains and controls the browser history.
 */
function editPolicy(id, history) {
  axios.get( `/api/policies/edit_${id}`).then(
    (response) => {
      console.log(response.data);
      const policy = JSON.parse(response.data.fieldValues);
      policy.id = response.data.id;
      history.push({
        pathname: response.data.queryOrigin,
        state: policy,
      });
    },
    (error) => {
      console.log(error);
    }
  );
}

/**
 * [Unused ad the moment!]
* Tries to connect with the backend to retrieve the given policy.
 * If the operation was successful, the policy viewer page is opened 
 * and all values from the template policy are pre-filled in the 
 * code areas.
 * @param {number} id the id of the policy that should be deleted
 * @param {object} history object that contains and controls the browser history.
 */
function viewPolicy(id, history) {
  axios.get( `/api/policies/edit_${id}`).then(
    (response) => {
      const fieldValues = JSON.parse(response.data.fieldValues);
      fieldValues.id = response.data.id
      const url = response.data.policyType
      axios.post( url, fieldValues).then(
        (response) => {
          axios
            .post(
               "/policy/initialTechnologyDependentPolicy",
              response.data
            )
            .then(
              (responseTDP) => {
                var dict = {
                  jsonPolicy: JSON.stringify(response.data, null, 2),
                  dtPolicy: responseTDP.data,
                };
                history.push({
                  pathname: "/ODRLCreator",
                  state: dict,
                });
              },
              (error) => {
                console.log(error);
              }
            );
        },
        (error) => {
          console.log(error);
        }
      );
    },
    (error) => {
      console.log(error);
    }
  );
}

export { getAllPolicies, exportPolicy, deletePolicy, editPolicy, viewPolicy };
