import axios from "axios";
const BASE_URL = "http://localhost:9090";

function getAllPolicies(data, setData) {
  axios.get(BASE_URL + "/api/policies").then(
    (response) => {
      setData(response.data);
    },
    (error) => {
      console.log(error);
    }
  );
}

function exportPolicy(id, fileName, contentType) {
  axios.get(BASE_URL + `/api/policies/export_${id}`).then(
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

function deletePolicy(id, data, setData) {
  axios.delete(BASE_URL + `/api/policies/${id}`).then(
    (response) => {
      getAllPolicies(data, setData);
    },
    (error) => {
      console.log(error);
    }
  );
}

function editPolicy(id, history) {
  axios.get(BASE_URL + `/api/policies/edit_${id}`).then(
    (response) => {
      console.log(response.data);
      const policy = JSON.parse(response.data.fieldValues);
      policy.id = response.data.id;
      history.push({
        pathname: response.data.policyType,
        state: policy,
      });
    },
    (error) => {
      console.log(error);
    }
  );
}

function viewPolicy(id, history) {
  axios.get(BASE_URL + `/api/policies/edit_${id}`).then(
    (response) => {
      const fieldValues = JSON.parse(response.data.fieldValues);
      fieldValues.id = response.data.id
      const url = response.data.policyType
      axios.post(BASE_URL + url, fieldValues).then(
        (response) => {
          axios
            .post(
              BASE_URL + "/policy/initialTechnologyDependentPolicy",
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
