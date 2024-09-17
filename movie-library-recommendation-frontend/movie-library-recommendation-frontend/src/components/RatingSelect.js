import { useState } from "react";
import styled from "styled-components";

const getTextColor = (theme) => theme.colors.text === '#000000' ? '#ffffff' : '#000000';

const RatingList = styled.ul`
    display: flex;
    justify-content: center;
    padding: 0;
    list-style: none;
    margin: 1rem 0;

    li {
        margin: 0 0.5rem;
    }

    input[type="radio"] {
        display: none;

        &:checked + label {
            background-color: var(--color-primary);
            color: white;
            transform: scale(1.2);
        }
    }

    label {
        display: inline-block;
        width: 2rem;
        height: 2rem;
        line-height: 2rem;
        border-radius: 50%;
        background-color: #f0f0f0;
        color: ${({ theme }) => getTextColor(theme)};
        text-align: center;
        font-size: 1rem;
        cursor: pointer;
        transition: all 0.3s ease;

        &:hover {
            background-color: var(--color-primary-light);
        }
    }
`;

function RatingSelect({ select }) {
    const [selected, setSelected] = useState(10);

    const handleChange = (e) => {
        const value = +e.currentTarget.value;
        setSelected(value);
        select(value);
    };

    return (
        <RatingList>
            {[...Array(10)].map((_, i) => {
                const value = i + 1;
                return (
                    <li key={value}>
                        <input
                            type="radio"
                            id={`num${value}`}
                            name="rating"
                            value={value}
                            onChange={handleChange}
                            checked={selected === value}
                        />
                        <label htmlFor={`num${value}`}>{value}</label>
                    </li>
                );
            })}
        </RatingList>
    );
}

export default RatingSelect;
